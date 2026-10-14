const PROXY_CONFIG = {
    "/api": {
      target: "https://didactic-enigma-7rg9gr99pw3ppr4-8080.app.github.dev",
      secure: true,
      changeOrigin: true,
      logLevel: "debug"  
    }
};

module.exports = PROXY_CONFIG;