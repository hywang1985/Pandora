package com.pandorabox.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.pandorabox.domain.Article;

@Repository("articleDao")
public class BaseArticleDao extends BaseGenericDataAccessor<Article, Integer> implements ArticleDao {

    private static final String GET_ARTICLES = "from Article";

    private static final String GET_ARTICLES_BY_TITLE = "from Article article where article.title like ?";

    private static final String GET_PREVIOUS_ARTICLES = "from Article article where article.articleId < ? order by article.articleId desc";

    private static final String GET_NEXT_ARTICLES = "from Article article where article.articleId > ? order by article.articleId asc";

    private static final String GET_RANDOM_ARTICLE = "from Article article ORDER BY rand()";

    public BaseArticleDao() {

    }

    @Override
    public List<Article> getByTitle(String articleTitle) {
        return find(GET_ARTICLES_BY_TITLE, articleTitle);
    }

    /**
     * 分页效果
     * */
    @SuppressWarnings("unchecked")
    @Deprecated
    public List<Article> getArticlesByPage(int start, int count) {
        Query query = createQuery(GET_ARTICLES).setFirstResult(start).setMaxResults(count);
        return query.list();
    }

    @Override
    @Deprecated
    public List<Article> getArticles() {
        return loadAll();
    }

    @SuppressWarnings("unchecked")
    @Override
    @Deprecated
    public List<Article> getPreviousArticles(int articleId, int count) {
        Query query = createQuery(GET_PREVIOUS_ARTICLES, articleId).setMaxResults(count);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    @Deprecated
    public List<Article> getNextArticles(int articleId, int count) {
        Query query = createQuery(GET_NEXT_ARTICLES, articleId).setMaxResults(count);
        return query.list();
    }

    @Override
    public Article getRandomArticle(int previousId, Query q) {
        Query query = q == null ? createQuery(GET_RANDOM_ARTICLE).setMaxResults(1) : q;
        Article toReturn = null;
        Article find = (Article) query.uniqueResult();
        if (previousId < 0) {
            return find;
        }
        if (find != null && find.getArticleId() >= 0 && previousId >= 0 && find.getArticleId() == previousId) {
            toReturn = getRandomArticle(previousId, query);
        } else {
            toReturn = find;
        }
        return toReturn;
    }
}
