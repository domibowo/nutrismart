import client from "shared/http-client/Client";

export async function login(form) {
  const response = await client.post("sign-in", form);
  return response;
}
