import client from "shared/http-client/Client";

export async function getCart(id) {
  const response = await client.get("/account/transaction/status/" + id);
  return response;
}

export async function deleteCart(idDetail, transaction) {
  const response = await client.put(
    "/account/transaction/cart/" + idDetail,
    transaction
  );
  return response;
}

export async function updateCart(transaction) {
  console.log(`MASUK SERVICE`);
  const account = {
    id: transaction.account.id,
  };
  transaction.account = account;
  console.log(`FORM : `, transaction);
  const response = await client.put("/account/transaction/update", transaction);
  return response;
}

export async function addToCart(transaction) {
  const response = await client.post("/account/transaction/cart", transaction);
  return response;
}

export async function setVoucherToTransaction(id, transaction) {
  const response = await client.put(
    "/account/transaction/cart/voucher/" + id,
    transaction
  );
  return response;
}

export async function commitTransaction(transactionId) {
  const response = await client.put(
    "/account/transaction/purchased/" + transactionId
  );
  return response;
}
