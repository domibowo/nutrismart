import client from "shared/http-client/Client";

export async function getProduct(page) {
  const size = 12;
  const response = await client.get(`product?page=${page}&size=${size}`);
  return response;
}
