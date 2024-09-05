import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;

public class IssueTests extends TestBase {
    private static final String REPO = "eroshenkoam/allure-example";
    private static final int ISSUE = 89;

    @Test
    void simpleSelenideIssueSearchTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        open("");
        $("[data-target='qbsearch-input.inputButtonText']").click();
        $("#query-builder-test").click();
        $("#query-builder-test"). setValue("eroshenkoam/allure-example").pressEnter();
        $(linkText("eroshenkoam/allure-example")).click();
        $("#issues-tab").click();
        $(withText("#89")).should(exist);
    }

    @Test
    void withLambdaIssueSearchTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        step("Открываем главную страницу", () -> {
            open("");
        });
        step("Ищем репозиторий" + REPO, () -> {
            $("[data-target='qbsearch-input.inputButtonText']").click();
            $("#query-builder-test").click();
            $("#query-builder-test").setValue(REPO).pressEnter();
        });
        step("Открываем репозиторий" + REPO, () -> {
            $(linkText(REPO)).click();
        });
        step("Открываем таб Issues", () -> {
            $("#issues-tab").click();
        });
        step("Проверяем наличие Issue с номером " + ISSUE, () -> {
            $(withText("#" + ISSUE)).should(exist);
        });
    }

    @Test
    void withAnnotatedStepIssueSearchTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        WebSteps steps = new WebSteps();
        steps.openMainPage();
        steps.searchRepository(REPO);
        steps.openRepository(REPO);
        steps.openTabIssue();
        steps.shouldFindIssueWithNumber(ISSUE);
    }
}
