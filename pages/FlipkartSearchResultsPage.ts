import { Page, expect } from '@playwright/test';

export class FlipkartSearchResultsPage {
  private results = this.page.locator('a[href*="/p/"]');

  constructor(private page: Page) {}

  async waitForResults() {
    await this.page.waitForURL('**/search**');
    await expect(this.results.first()).toBeVisible();
  }

  async getResultCount(): Promise<number> {
    return this.results.count();
  }

  async getFirstResultTitle(): Promise<string> {
    const text = await this.results.first().textContent();
    return text ?? '';
  }

  async assertNoError() {
    await expect(this.page.getByText(/something went wrong/i)).not.toBeVisible();
  }
}
