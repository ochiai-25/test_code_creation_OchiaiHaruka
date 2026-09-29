package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト レポート機能
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

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

		visibilityTimeout(By.tagName("h2"), 6);
		// assert
		String pageTitle = webDriver.getTitle();
		assertEquals("コース詳細 | LMS", pageTitle);

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {

		// 一覧の行が表示されるまで待機
		visibilityTimeout(
				By.cssSelector("tbody tr"), 6);

		// 一覧の行をすべて取得
		List<WebElement> rows = webDriver.findElements(
				By.cssSelector("tbody tr"));

		WebElement report = null;

		// 「未提出」の行を探す
		for (WebElement row : rows) {
			if (row.getText().contains("未提出")) {
				report = row;
				break;
			}
		}

		// 「未提出」の行にある「詳細」ボタンを取得
		WebElement detailButton = report.findElement(
				By.cssSelector("input[type='submit'][value='詳細']"));

		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		js.executeScript("arguments[0].click();", detailButton);

		// セクション詳細画面が表示されるまで待機
		visibilityTimeout(
				By.cssSelector("#sectionDetail h2"), 6);

		String title = "セクション詳細 | LMS";
		assertEquals(title, webDriver.getTitle());

		// エビデンスを保存
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		visibilityTimeout(By.tagName("h2"), 5);

		WebElement reportButton = webDriver.findElement(By.cssSelector("input[value='日報【デモ】を提出する']"));

		reportButton.click();

		visibilityTimeout(By.tagName("body"), 5);

		assertTrue(webDriver.findElement(By.tagName("h2")).isDisplayed());

		visibilityTimeout(By.tagName("h2"), 5);

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {

		webDriver.findElement(By.id("content_0")).sendKeys("本日の研修内容を報告します。");

		webDriver.findElement(By.cssSelector("button[type='submit']")).click();

		WebElement inputSubmitValue = webDriver.findElement(
				By.cssSelector("input[type='submit'][value='提出済み日報【デモ】を確認する']"));
		String inputValue = inputSubmitValue.getAttribute("value");
		// println 確認用
		//		System.out.println("ボタン名：" + inputValue);

		// assert
		assertEquals("提出済み日報【デモ】を確認する", inputValue);
		visibilityTimeout(By.tagName("h2"), 6);

		getEvidence(new Object() {
		});
	}

}
