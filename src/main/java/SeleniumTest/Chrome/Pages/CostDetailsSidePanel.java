package SeleniumTest.Chrome.Pages;

import SeleniumTest.AbstractPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static SeleniumTest.ThreadLocalDriver.getDriver;

@Getter
public class CostDetailsSidePanel extends AbstractPage {
    private static final String SIDE_PANEL_XPATH = "//h2[text() = 'Cost details']/../..";
    //general elements
    private static final String ADD_ESTIMATE_BUTTON_XPATH = SIDE_PANEL_XPATH + "//*[contains(text(), 'Add to estimate')]";
    private static final String TOTAL_ESTIMATED_COAST_XPATH = "//div[text()='Estimated cost']//following::div/label";
    private static final String OPEN_DETAILED_VIEW_LINK_XPATH = SIDE_PANEL_XPATH + "//a[@aria-label='Open detailed view']";

    public void addToEstimateButtonClick() {
        getDriver().findElement(By.xpath(ADD_ESTIMATE_BUTTON_XPATH)).click();
    }

    public String getTotalEstimatedCost() {
        return getDriver().findElement(By.xpath(TOTAL_ESTIMATED_COAST_XPATH)).getText();
    }

    public ProductDetailSingleItem getProductDetailsSingleItem(String itemTitle) {
        return getProductDetailSingleItems().stream()
                .filter(item -> item.getTitle().equals(itemTitle))
                .findAny()
                .orElseThrow(() -> new NullPointerException("No product with title '%s'".formatted(itemTitle)));
    }

    public String getDetailedViewLink() {
        return getOpenDetailedView().getAttribute("href");
    }


    public WebElement getOpenDetailedView() {
        return getDriver().findElement(By.xpath(OPEN_DETAILED_VIEW_LINK_XPATH));
    }

    private List<ProductDetailSingleItem> getProductDetailSingleItems() {
        List<WebElement> elements = getDriver().findElements(By.xpath(ProductDetailSingleItem.PRODUCT_DETAILS_SINGLE_ITEM_XPATH));
        return elements.stream()
                .map(ProductDetailSingleItem::new)
                .toList();
    }

    public static class ProductDetailSingleItem {
        private final WebElement element;
        private static final String PRODUCT_DETAILS_SINGLE_ITEM_XPATH = SIDE_PANEL_XPATH + "//div[contains(@aria-label, 'Edit')]";
        private static final String PRODUCT_DETAILS_SINGLE_ITEM_COST_XPATH = ".//div[3]";
        private static final String PRODUCT_DETAILS_SINGLE_ITEM_MENU_BUTTON_XPATH = ".//button[@aria-label = 'More options']";
        private static final String PRODUCT_MENU_DROPDOWN_ELEMENT_XPATH_TEMPLATE = "//body/div/div/ul//li[@role='menuitem']//span[text()='%s']/../..";
        private static final String VIEW_DETAILS_XPATH = PRODUCT_MENU_DROPDOWN_ELEMENT_XPATH_TEMPLATE.formatted("View details");
        private static final String PRODUCT_DETAILS_SINGLE_ITEM_RENAME_ITEM_XPATH = PRODUCT_MENU_DROPDOWN_ELEMENT_XPATH_TEMPLATE.formatted("Rename item");
        private static final String PRODUCT_DETAILS_SINGLE_ITEM_DELETE_ITEM = PRODUCT_MENU_DROPDOWN_ELEMENT_XPATH_TEMPLATE.formatted("Delete item");

        public ProductDetailSingleItem(WebElement element) {
            this.element = element;
        }

        public String getItemCost() {
            return this.element.findElement(By.xpath(PRODUCT_DETAILS_SINGLE_ITEM_COST_XPATH)).getText();
        }

        public ProductDetailSingleItem openProductMenuOptions() {
            this.element.findElement(By.xpath(PRODUCT_DETAILS_SINGLE_ITEM_MENU_BUTTON_XPATH)).click();
            return this;
        }

        public void openProductDetailsWindow() {
            getDriver().findElement(By.xpath(VIEW_DETAILS_XPATH)).click();
        }

        protected String getTitle() {
            return this.element.findElement(By.xpath(".//textarea")).getText();
        }
    }

}