const { defineConfig } = require('cypress')
const {FRONTEND} = require("./reactapp/scholarizer/src/WebAddresses");

module.exports = defineConfig({
  e2e: {
    baseUrl: FRONTEND,
  },
})
