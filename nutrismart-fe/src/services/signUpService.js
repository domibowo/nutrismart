import client from "shared/http-client/Client";

export async function saveSignUp(form) {
  console.log("service signup");
  const response = await client.post("sign-up", form);
  return response;
}
