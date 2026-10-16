import { Page, expect } from '@playwright/test';

export class FlipkartHomePage {
  constructor(private page: Page) {}

  async goto() {
    await this.page.goto('/');
  }

  async dismissPopup() {
    try {
      await this.page.keyboard.press('Escape');
      // Brief wait for popup to dismiss
      await this.page.waitForTimeout(500);
    } catch {
      // Popup may not always appear — safe to ignore
    }
  }

  async search(term: string) {
    const searchInput = this.page.locator('input[name="q"]:not([readonly])');
    await expect(searchInput).toBeVisible();
    await searchInput.fill(term);
    await searchInput.press('Enter');
  }
}
