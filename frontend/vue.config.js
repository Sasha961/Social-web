module.exports = {
  runtimeCompiler: true,
    devServer: {
      proxy: {
        '/': {
          target: 'http://5.63.154.191:8088',
          changeOrigin: true,
          ws: true,
          secure: false,
          onProxyReq: function(request) {
            request.setHeader("origin", "http://5.63.154.191:8088");
          },
        },
      },
  }

}
