const { createProxyMiddleware } = require("http-proxy-middleware");

module.exports = function (app) {
  createProxyMiddleware({
    target: "http://10.10.11.15:5003/",
    changeOrigin: true,
  })
};
