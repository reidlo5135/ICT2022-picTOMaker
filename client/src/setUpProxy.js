const {createProxyMiddleware} = require('http-proxy-middleware');

module.exports = function (app) {
    app.use(createProxyMiddleware('/v1/api', {
        // target: 'http://localhost:8080',
        target: 'http://ec2-52-79-56-189.ap-northeast-2.compute.amazonaws.com/',
        changeOrigin: true
    }));
}