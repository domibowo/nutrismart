import client from "shared/http-client/Client";

export async function getVoucher() {
  const page = 1;
  const size = 20;
  const response = await client.get(`/voucher?page=${page}&size=${size}`);
  return response;
}
