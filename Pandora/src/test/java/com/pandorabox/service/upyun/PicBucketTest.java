package com.pandorabox.service.upyun;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pandorabox.service.BasePandoraServiceTest;
import com.pandorabox.service.upyun.UpYunService.PARAMS;

/**
 * 图片类空间的Test，一般性操作参考文件空间的Test
 * 
 * @see FileBucketTest.java <p>
 *      注意：直接使用部分图片处理功能后，将会丢弃原图保存处理后的图片
 * @deprecated 现在使用又拍云的表单API方式提交图片
 */
public class PicBucketTest extends BasePandoraServiceTest {

	// 运行前先设置好以下三个参数
	private static final String BUCKET_NAME = "pandora001";
	private static final String USER_NAME = "tester001";
	private static final String USER_PWD = "tester001";

	/** 绑定的域名 */
	private static final String URL = "http://" + BUCKET_NAME
			+ ".b0.upaiyun.com";

	/** 根目录 */
	private static final String DIR_ROOT = "/";

	/** 上传到upyun的图片名 */
	private static final String PIC_NAME = "sample.jpeg";

	/** 本地待上传的测试文件 */
	private static final java.net.URL SAMPLE_PIC_FILE = PicBucketTest.class
			.getResource(PIC_NAME);

	@Autowired
	private UpYunService upyun;

	private static boolean isUpYunServiceSetUp;

	static {
		File picFile = null;
		try {
			picFile = new File(SAMPLE_PIC_FILE.toURI().getPath());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		if (picFile == null || !picFile.exists() || !picFile.isFile()) {
			System.out.println("本地待上传的测试文件不存在!");
		}
	}

	@Before
	public void init() {
		if (!isUpYunServiceSetUp) { //若没有设置upyun，则设置
			upyun.setBucketName(BUCKET_NAME);
			upyun.setUserName(USER_NAME);
			upyun.setPassword(USER_PWD);

			// ****** 可选设置 begin ******

			// 切换 API 接口的域名接入点，默认为自动识别接入点
			// upyun.setApiDomain(UpYun.ED_AUTO);

			// 设置连接超时时间，默认为30秒
			// upyun.setTimeout(60);

			// 设置是否开启debug模式，默认不开启
			upyun.setDebug(true);

			// ****** 可选设置 end ******

			/*
			 * 注：图片的所有参数均可以自由搭配使用
			 */
			isUpYunServiceSetUp = true;
		}
	}

	/**
	 * 上传文件
	 * 
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	@Test
	public void testWriteFile() throws IOException, URISyntaxException {
		// 要传到upyun后的文件路径
		String filePath = DIR_ROOT + PIC_NAME;

		// 本地待上传的图片文件
		File file = new File(SAMPLE_PIC_FILE.toURI().getPath());

		// 设置待上传文件的 Content-MD5 值
		// 如果又拍云服务端收到的文件MD5值与用户设置的不一致，将回报 406 NotAcceptable 错误
		upyun.setContentMD5(UpYunService.md5(file));

		// 设置待上传文件的"访问密钥"
		// 注意：
		// 仅支持图片空！，设置密钥后，无法根据原文件URL直接访问，需带URL后面加上（缩略图间隔标志符+密钥）进行访问
		// 举例：
		// 如果缩略图间隔标志符为"!"，密钥为"bac"，上传文件路径为"/folder/test.jpg"，
		// 那么该图片的对外访问地址为：http://空间域名 /folder/test.jpg!bac
		upyun.setFileSecret("bac");

		// 上传文件，并自动创建父级目录（最多10级）
		boolean result = upyun.writeFile(filePath, file, true);

		System.out.println(filePath + " 上传" + isSuccess(result));

		// 获取上传文件后的信息（仅图片空间有返回数据）
		System.out.println("\r\n****** " + file.getName() + " 的图片信息 *******");
		// 图片宽度
		System.out.println("图片宽度:" + upyun.getPicWidth());
		// 图片高度
		System.out.println("图片高度:" + upyun.getPicHeight());
		// 图片帧数
		System.out.println("图片帧数:" + upyun.getPicFrames());
		// 图片类型
		System.out.println("图片类型:" + upyun.getPicType());

		System.out.println("****************************************\r\n");

		System.out.println("若设置过访问密钥(bac)，且缩略图间隔标志符为'!'，则可以通过以下路径来访问图片：");
		System.out.println(URL + filePath + "!bac");
		System.out.println();
	}

	@Test
	public void testDeleteFile() {
		String filePath = DIR_ROOT + PIC_NAME;
		// 删除文件
		boolean result = upyun.deleteFile(filePath);

		System.out.println(filePath + " 删除" + isSuccess(result));
		System.out.println();
	}

	/**
	 * 图片做缩略图
	 * <p>
	 * 注意：若使用了缩略图功能，则会丢弃原图
	 * 
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	@Test
	public void testGmkerl() throws IOException, URISyntaxException {

		// 要传到upyun后的文件路径
		String filePath = DIR_ROOT + "gmkerl.jpg";

		// 本地待上传的图片文件
		File file = new File(SAMPLE_PIC_FILE.toURI().getPath());

		// 设置缩略图的参数
		Map<String, String> params = new HashMap<String, String>();

		// 设置缩略图类型，必须搭配缩略图参数值（KEY_VALUE）使用，否则无效
		params.put(PARAMS.KEY_X_GMKERL_TYPE.getValue(),
				PARAMS.VALUE_FIX_BOTH.getValue());

		// 设置缩略图参数值，必须搭配缩略图类型（KEY_TYPE）使用，否则无效
		params.put(PARAMS.KEY_X_GMKERL_VALUE.getValue(), "150x150");

		// 设置缩略图的质量，默认 95
		params.put(PARAMS.KEY_X_GMKERL_QUALITY.getValue(), "95");

		// 设置缩略图的锐化，默认锐化（true）
		params.put(PARAMS.KEY_X_GMKERL_UNSHARP.getValue(), "true");

		// 若在 upyun 后台配置过缩略图版本号，则可以设置缩略图的版本名称
		// 注意：只有存在缩略图版本名称，才会按照配置参数制作缩略图，否则无效
		params.put(PARAMS.KEY_X_GMKERL_THUMBNAIL.getValue(), "small");

		// 上传文件，并自动创建父级目录（最多10级）
		boolean result = upyun.writeFile(filePath, file, true, params);

		System.out.println(filePath + " 制作缩略图" + isSuccess(result));
		System.out.println("可以通过该路径来访问图片：" + URL + filePath);
		System.out.println();
	}

	/**
	 * 图片旋转
	 * 
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	@Test
	public void testRotate() throws IOException, URISyntaxException {

		// 要传到upyun后的文件路径
		String filePath = DIR_ROOT + "rotate.jpg";

		// 本地待上传的图片文件
		File file = new File(SAMPLE_PIC_FILE.toURI().getPath());

		// 图片旋转功能具体可参考：http://wiki.upyun.com/index.php?title=图片旋转
		Map<String, String> params = new HashMap<String, String>();

		// 设置图片旋转：只接受"auto"，"90"，"180"，"270"四种参数
		params.put(PARAMS.KEY_X_GMKERL_ROTATE.getValue(),
				PARAMS.VALUE_ROTATE_90.getValue());

		// 上传文件，并自动创建父级目录（最多10级）
		boolean result = upyun.writeFile(filePath, file, true, params);

		System.out.println(filePath + " 图片旋转" + isSuccess(result));
		System.out.println("可以通过该路径来访问图片：" + URL + filePath);
		System.out.println();
	}

	/**
	 * 图片裁剪
	 * 
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	@Test
	public void testCrop() throws IOException, URISyntaxException {

		// 要传到upyun后的文件路径
		String filePath = DIR_ROOT + "crop.jpg";

		// 本地待上传的图片文件
		File file = new File(SAMPLE_PIC_FILE.toURI().getPath());

		// 图片裁剪功能具体可参考：http://wiki.upyun.com/index.php?title=图片裁剪
		Map<String, String> params = new HashMap<String, String>();

		// 设置图片裁剪，参数格式：x,y,width,height
		params.put(PARAMS.KEY_X_GMKERL_CROP.getValue(), "50,50,300,300");

		// 上传文件，并自动创建父级目录（最多10级）
		boolean result = upyun.writeFile(filePath, file, true, params);

		System.out.println(filePath + " 图片裁剪" + isSuccess(result));
		System.out.println("可以通过该路径来访问图片：" + URL + filePath);
		System.out.println();
	}

	private String isSuccess(boolean result) {
		return result ? " 成功" : " 失败";
	}
}
