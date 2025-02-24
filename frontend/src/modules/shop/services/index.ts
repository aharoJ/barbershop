// @/modules/shop/services/index.ts
import { ShopServiceImpl } from "./shop.service.impl";
import type { ShopService } from "./shop.service";

// Create the real service
const shopServiceImpl: ShopService = new ShopServiceImpl();

export { shopServiceImpl };
