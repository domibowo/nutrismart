import client from "shared/http-client/Client";

export async function getProfile(id) {
  const response = await client.get("/account/profile/" + id);
  return response;
}

export async function updateProfile(form) {
  const body = {
    email: form.email,
    id: form.id,
    userName: form.userName,
    password: form.password,
    profile: form.profile,
  };

  const response = await client.put("/account/profile", body);
  return response;
}
