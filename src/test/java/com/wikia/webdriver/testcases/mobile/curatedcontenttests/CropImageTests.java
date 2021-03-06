package com.wikia.webdriver.testcases.mobile.curatedcontenttests;

import com.wikia.webdriver.common.contentpatterns.MercuryPaths;
import com.wikia.webdriver.common.contentpatterns.MobileSubpages;
import com.wikia.webdriver.common.contentpatterns.MobileWikis;
import com.wikia.webdriver.common.core.Assertion;
import com.wikia.webdriver.common.core.annotations.Execute;
import com.wikia.webdriver.common.core.annotations.InBrowser;
import com.wikia.webdriver.common.core.drivers.Browser;
import com.wikia.webdriver.common.core.helpers.Emulator;
import com.wikia.webdriver.common.core.helpers.User;
import com.wikia.webdriver.common.templates.NewTestTemplate;
import com.wikia.webdriver.elements.common.Navigate;
import com.wikia.webdriver.elements.communities.mobile.pages.curatedcontent.CuratedMainPagePageObject;
import com.wikia.webdriver.elements.communities.mobile.pages.curatedcontent.EditorHomePageObject;
import com.wikia.webdriver.elements.communities.mobile.pages.curatedcontent.curatededitorform.ItemFormPageObject;
import com.wikia.webdriver.elements.communities.mobile.pages.curatedcontent.imageupload.CroppingToolPageObject;
import com.wikia.webdriver.elements.communities.mobile.pages.curatedcontent.imageupload.SearchForImagePageObject;
import com.wikia.webdriver.elements.communities.mobile.pages.curatedcontent.imageupload.UploadImageModalComponentObject;

import org.testng.annotations.Test;
@Test(groups = "Mercury_CropImage")
@Execute(
    onWikia = MobileWikis.MERCURY_EMPTY_CC_EDITOR,
    asUser = User.STAFF
)
@InBrowser(
    browser = Browser.CHROME,
    emulator = Emulator.GOOGLE_NEXUS_5
)
public class CropImageTests extends NewTestTemplate {

  private static final String SEARCH_IMAGE_QUERY = "U";
  private static String ARTICLE_TITLE_SELECTOR = ".wiki-page-title";

  private CuratedMainPagePageObject curatedMainPage;
  private EditorHomePageObject editor;
  private ItemFormPageObject itemForm;
  private UploadImageModalComponentObject imageModal;
  private SearchForImagePageObject search;
  private CroppingToolPageObject croppingTool;
  private Navigate navigate;

  private void init() {
    this.curatedMainPage = new CuratedMainPagePageObject();
    this.editor = new EditorHomePageObject();
    this.itemForm = new ItemFormPageObject();
    this.imageModal = new UploadImageModalComponentObject(driver);
    this.search = new SearchForImagePageObject(driver);
    this.croppingTool = new CroppingToolPageObject(driver);
    this.navigate = new Navigate();
  }

  @Test(groups = "MercuryCropImageTest_001")
  public void MercuryCropImageTest_001_cropOptionInModal() {
    init();

    navigate.toPageByPath(MobileSubpages.ECC_MAIN_PAGE);
    curatedMainPage.isCuratedElementVisible(ARTICLE_TITLE_SELECTOR);

    navigate.toPageByPath(MercuryPaths.ROOT_MAIN_EDIT);
    editor.clickAddFeaturedContent();
    itemForm.clickOnImage();

    Assertion.assertFalse(imageModal.isCropOptionEnabled(),
                          "Crop option enabled - Should be disabled");

    imageModal.clickSearchForImageButton();
    search.type(SEARCH_IMAGE_QUERY);
    search.clickOnImage(0);
    croppingTool.clickDoneButton();
    itemForm.clickOnImage();

    Assertion
        .assertTrue(imageModal.isCropOptionEnabled(), "Crop option disabled - Should be enabled");

    imageModal.selectCrop();
    Assertion.assertTrue(croppingTool.isCropperLoaded(), "Cropper not loaded - Should be loaded");
  }
}
