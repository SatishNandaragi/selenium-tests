package com.wikia.webdriver.testcases.desktop.visualeditor;

import com.wikia.webdriver.common.contentpatterns.PageContent;
import com.wikia.webdriver.common.core.configuration.Configuration;
import com.wikia.webdriver.common.dataprovider.VisualEditorDataProvider.Formatting;
import com.wikia.webdriver.common.properties.Credentials;
import com.wikia.webdriver.common.templates.NewTestTemplate;
import com.wikia.webdriver.pageobjectsfactory.componentobject.visualeditordialogs.VisualEditorSaveChangesDialog;
import com.wikia.webdriver.pageobjectsfactory.pageobject.WikiBasePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.article.ArticlePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.visualeditor.VisualEditorPageObject;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class VisualEditorMultiplePublishTests extends NewTestTemplate {

  Credentials credentials = Configuration.getCredentials();
  VisualEditorPageObject ve;
  VisualEditorSaveChangesDialog save;
  ArticlePageObject article;
  WikiBasePageObject base;

  @BeforeMethod(alwaysRun = true)
  public void setup() {
    base = new WikiBasePageObject();
    base.loginAs(credentials.userNameVEPreferred, credentials.passwordVEPreferred, wikiURL);
  }

  @Test(groups = {"VisualEditorMultiplePublish", "VisualEditorMultiplePublish_001"})
  public void VisualEditorMultiplePublish_001() {
    String targetText = PageContent.ARTICLE_TEXT;
    String articleName = base.getNameForArticle();
    article = new ArticlePageObject().open(articleName);
    ve = article.openVEOnArticle(wikiURL, articleName);
    ve.verifyVEToolBarPresent();
    ve.verifyEditorSurfacePresent();
    article = ve.clickVEEditAndPublish(targetText);
    article.verifyFormattingFromVE(Formatting.PARAGRAPH, targetText);
    targetText = PageContent.ARTICLE_TEXT_EDIT + targetText;
    ve = article.openVEOnArticle(wikiURL, articleName);
    ve.verifyVEToolBarPresent();
    ve.verifyEditorSurfacePresent();
    article = ve.clickVEEditAndPublish(PageContent.ARTICLE_TEXT_EDIT);
    article.verifyFormattingFromVE(Formatting.PARAGRAPH, targetText);
    targetText = PageContent.ARTICLE_TEXT_SECOND_EDIT + targetText;
    ve = article.openVEOnArticle(wikiURL, articleName);
    ve.verifyVEToolBarPresent();
    ve.verifyEditorSurfacePresent();
    article = ve.clickVEEditAndPublish(PageContent.ARTICLE_TEXT_SECOND_EDIT);
    article.verifyFormattingFromVE(Formatting.PARAGRAPH, targetText);
  }
}
