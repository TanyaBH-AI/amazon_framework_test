import { test, expect } from '@playwright/test';
import { FlipkartHomePage } from '../../pages/FlipkartHomePage';
import { FlipkartSearchResultsPage } from '../../pages/FlipkartSearchResultsPage';

test.describe('Flipkart Search', () => {
  test('search returns relevant results for "cornflakes"', async ({ page }) => {
    const homePage = new FlipkartHomePage(page);
    const searchResultsPage = new FlipkartSearchResultsPage(page);

    // Navigate to home page
    await homePage.goto();

    // Dismiss login popup if it appears
    await homePage.dismissPopup();

    // Search for cornflakes
    await homePage.search('cornflakes');

    // Wait for search results to load
    await searchResultsPage.waitForResults();

    // Assert results list has at least one item
    const resultCount = await searchResultsPage.getResultCount();
    expect(resultCount).toBeGreaterThanOrEqual(1);

    // Assert first result contains "cornflakes" (case-insensitive)
    const firstResultTitle = await searchResultsPage.getFirstResultTitle();
    expect(firstResultTitle.toLowerCase()).toContain('cornflakes');

    // Assert no error is shown
    await searchResultsPage.assertNoError();
  });
});
