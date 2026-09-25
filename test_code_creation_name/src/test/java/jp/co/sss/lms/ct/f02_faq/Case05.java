package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト よくある質問機能
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		webDriver.get("http://localhost:8080/lms/");

		assertEquals("ログイン | LMS", webDriver.getTitle());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		WebElement userId = webDriver.findElement(By.id("loginId"));
		WebElement password = webDriver.findElement(By.id("password"));
		WebElement loginButton = webDriver.findElement(By.cssSelector("input.btn-primary"));

		String title = "コース詳細 | LMS";

		userId.clear();

		password.clear();

		userId.sendKeys("StudentAA02");
		password.sendKeys("Asdfg12345");

		loginButton.click();

		assertEquals(title, webDriver.getTitle());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		getEvidence(new Object() {
		});

		WebElement menuLink = webDriver.findElement(By.cssSelector("a.dropdown-toggle"));

		menuLink.click();

		WebElement helpLink = webDriver.findElement(By.linkText("ヘルプ"));

		String title = "ヘルプ | LMS";

		helpLink.click();

		getEvidence(new Object() {
		});

		assertEquals(title, webDriver.getTitle());
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		WebElement questionLink = webDriver.findElement(By.linkText("よくある質問"));
		String title = "よくある質問 | LMS";

		int waitTime = 10;

		questionLink.click();

		/**ブラウザで開いているタブを全部取得*/
		Object[] windowHandles = webDriver.getWindowHandles().toArray();

		/**Seleniumが操作するタブを、2つ目のタブに切り替え*/
		webDriver.switchTo().window((String) windowHandles[1]);

		visibilityTimeout(By.tagName("h2"), waitTime);

		getEvidence(new Object() {
		});

		/**別タブに開いたページのタイトルが「よくある質問｜LMS」になっているか*/
		assertEquals(title, webDriver.getTitle());
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
		webDriver.findElement(By.id("form")).sendKeys("ログイン");

		webDriver.findElement(By.cssSelector("input[type='submit']")).click();

		visibilityTimeout(By.cssSelector("table.sortabletable"), 5);

		assertTrue(webDriver.findElement(By.cssSelector("table.sortabletable")).isDisplayed());

		assertTrue(webDriver.getPageSource().contains("ログイン"));

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		webDriver.findElement(By.cssSelector("input[type='button']")).click();

		assertEquals("", webDriver.findElement(By.id("form")).getAttribute("value"));

		getEvidence(new Object() {
		});
	}
}
