import client from "shared/http-client/Client";

export async function getProductByCategory(id) {
  const size = 6;
  const response = await client.get(
    `/product/category/${id}?page=1&size=${size}`
  );
  return response;
}

export async function getProductByCategoryAndPage(id, page) {
  const size = 12;
  const response = await client.get(
    `/product/category/${id}?page=${page}&size=${size}`
  );
  return response;
}
