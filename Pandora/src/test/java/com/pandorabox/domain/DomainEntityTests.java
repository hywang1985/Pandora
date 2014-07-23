package com.pandorabox.domain;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pandorabox.domain.impl.BaseArticle;
import com.pandorabox.domain.impl.BaseFileDescriptor;
import com.pandorabox.domain.impl.BaseImageDescriptor;
import com.pandorabox.domain.impl.BaseUser;

/**
 * @author hywang This class is used to test domain entity methods including
 *         override ones.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class DomainEntityTests {

	private static BaseUser user1, user2, user3;

	private static BaseArticle article1, article2, article3;

	private static BaseImageDescriptor image1, image2, image3;

	private static BaseFileDescriptor file1, file2, file3;

	@BeforeClass
	public static void initStaticVarriables() {
		// Init users
		initUsers();
		// Init articles
		initArticles();
		// Init images
		initImages();
		// Init files
		initFiles();
	}

	private static void initUsers() {
		user1 = new BaseUser();
		user1.setUserId(1);
		user1.setEmail("jam_0917@sina.com");
		user1.setUsername("hywang");
		user1.setUrl("aaa");
		user2 = new BaseUser();
		user2.setUserId(1);
		user2.setEmail("jam_0917@sina.com");
		user2.setUsername("hywang");
		user2.setUrl("aaa");
		user3 = new BaseUser();
		user3.setUserId(1);
		user3.setEmail("jam_0917@sina.com");
		user3.setUsername("hywang");
		user3.setUrl("aaa");
	}
	
	private static void initArticles() {
		article1 = new BaseArticle();
		article1.setArticleId(1);
		article1.setAuthor(user1);
		
		article2 = new BaseArticle();
		article2.setArticleId(1);
		article2.setAuthor(user1);
		
		article3 = new BaseArticle();
		article3.setArticleId(1);
		article3.setAuthor(user1);
	}
	
	private static void initImages() {
		image1 = new BaseImageDescriptor();
		image1.setImageId(1);
		image1.setName("Fileblablabla1");
		image1.setUrl("http://xxx.com/asd.mp3");
		
		image2 = new BaseImageDescriptor();
		image2.setImageId(1);
		image2.setName("Fileblablabla1");
		image2.setUrl("http://xxx.com/asd.mp3");
		
		image3 = new BaseImageDescriptor();
		image3.setImageId(1);
		image3.setName("Fileblablabla1");
		image3.setUrl("http://xxx.com/asd.mp3");
		
	}
	
	private static void initFiles() {
		file1 = new BaseFileDescriptor();
		file1.setFileId(1);
		file1.setName("Fileblablabla1");
		file1.setUrl("http://xxx.com/asd.mp3");
		
		file2 = new BaseFileDescriptor();
		file2.setFileId(1);
		file2.setName("Fileblablabla1");
		file2.setUrl("http://xxx.com/asd.mp3");
		
		file3 = new BaseFileDescriptor();
		file3.setFileId(1);
		file3.setName("Fileblablabla1");
		file3.setUrl("http://xxx.com/asd.mp3");
	}

	/**
	 * The equals method implements an equivalence relation on non-null object
	 * references: It is reflexive: for any non-null reference value x,
	 * x.equals(x) should return true. It is symmetric: for any non-null
	 * reference values x and y, x.equals(y) should return true if and only if
	 * y.equals(x) returns true. It is transitive: for any non-null reference
	 * values x, y, and z, if x.equals(y) returns true and y.equals(z) returns
	 * true, then x.equals(z) should return true. It is consistent: for any
	 * non-null reference values x and y, multiple invocations of x.equals(y)
	 * consistently return true or consistently return false, provided no
	 * information used in equals comparisons on the objects is modified. For
	 * any non-null reference value x, x.equals(null) should return false.
	 * */
	@Test
	public void testUserEquals() {
		// reflexive
		Assert.assertTrue(user1.equals(user1));
		Assert.assertTrue(user2.equals(user2));
		Assert.assertTrue(user3.equals(user3));
		// symmetric
		Assert.assertTrue(user1.equals(user2) && user2.equals(user1));
		Assert.assertTrue(user2.equals(user1) && user1.equals(user2));
		// transitive
		Assert.assertTrue(user1.equals(user2) && user2.equals(user3)
				&& user1.equals(user3));
		// consistent
		Assert.assertTrue(user1.equals(user2));
		user2.setUserId(2);
		Assert.assertFalse(user1.equals(user2));
		user2.setUserId(1); // revert the modification
		// non-null equal
		Assert.assertFalse(user1.equals(null));
		Assert.assertFalse(user2.equals(null));
		Assert.assertFalse(user3.equals(null));
	}

	@Test
	public void testArticleEquals() {
		// reflexive
		Assert.assertTrue(article1.equals(article1));
		Assert.assertTrue(article2.equals(article2));
		Assert.assertTrue(article3.equals(article3));
		// symmetric
		Assert.assertTrue(article1.equals(article2) && article2.equals(article1));
		Assert.assertTrue(article2.equals(article1) && article1.equals(article2));
		// transitive
		Assert.assertTrue(article1.equals(article2) && article2.equals(article3)
				&& article1.equals(article3));
		// consistent
		Assert.assertTrue(article1.equals(article2));
		article2.setArticleId(2);
		Assert.assertFalse(article1.equals(article2));
		article2.setArticleId(1); // revert the modification
		// non-null equal
		Assert.assertFalse(article1.equals(null));
		Assert.assertFalse(article2.equals(null));
		Assert.assertFalse(article3.equals(null));
	}

	@Test
	public void testImageEquals() {
		// reflexive
		Assert.assertTrue(image1.equals(image1));
		Assert.assertTrue(image2.equals(image2));
		Assert.assertTrue(image3.equals(image3));
		// symmetric
		Assert.assertTrue(image1.equals(image2) && image2.equals(image1));
		Assert.assertTrue(image2.equals(image1) && image1.equals(image2));
		// transitive
		Assert.assertTrue(image1.equals(image2) && image2.equals(image3)
				&& image1.equals(image3));
		// consistent
		Assert.assertTrue(image1.equals(image2));
		image2.setImageId(2);
		Assert.assertFalse(image1.equals(image2));
		image2.setImageId(1); // revert the modification
		// non-null equal
		Assert.assertFalse(image1.equals(null));
		Assert.assertFalse(image2.equals(null));
		Assert.assertFalse(image3.equals(null));
	}

	@Test
	public void testFileEquals() {
		// reflexive
		Assert.assertTrue(file1.equals(file1));
		Assert.assertTrue(file2.equals(file2));
		Assert.assertTrue(file3.equals(file3));
		// symmetric
		Assert.assertTrue(file1.equals(file2) && file2.equals(file1));
		Assert.assertTrue(file2.equals(file1) && file1.equals(file2));
		// transitive
		Assert.assertTrue(file1.equals(file2) && file2.equals(file3)
				&& file1.equals(file3));
		// consistent
		Assert.assertTrue(file1.equals(file2));
		file2.setFileId(2);
		Assert.assertFalse(file1.equals(file2));
		file2.setFileId(1); // revert the modification
		// non-null equal
		Assert.assertFalse(file1.equals(null));
		Assert.assertFalse(file2.equals(null));
		Assert.assertFalse(file3.equals(null));
	}

}
