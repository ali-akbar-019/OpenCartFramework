package utilities;

import org.testng.annotations.DataProvider;

public class DataProviders {

	@DataProvider(name = "LoginData")
	public Object[][] getLoginData() {

		String path = System.getProperty("user.dir") + "\\testData\\TestData.xlsx";
		XLUtility xl = new XLUtility(path);

		Object[][] data = null;

		try {
			int rowCount = xl.getRowCount("LoginData"); // total rows (0-based)
			int colCount = xl.getCellCount("LoginData", 1); // columns in first data row

			data = new Object[rowCount][colCount];

			for (int i = 1; i <= rowCount; i++) { // start from row 1 (skip header)
				for (int j = 0; j < colCount; j++) {
					data[i - 1][j] = xl.getCellData("LoginData", i, j);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

	// ---------------- SEARCH DATA ----------------
	@DataProvider(name = "SearchData")
	public Object[][] getSearchData() {

		String path = System.getProperty("user.dir") + "\\testData\\TestData.xlsx";
		XLUtility xl = new XLUtility(path);

		Object[][] data = null;

		try {
			int rowCount = xl.getRowCount("SearchData");
			int colCount = xl.getCellCount("SearchData", 1);

			data = new Object[rowCount][colCount];

			for (int i = 1; i <= rowCount; i++) {
				for (int j = 0; j < colCount; j++) {
					data[i - 1][j] = xl.getCellData("SearchData", i, j);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}
}