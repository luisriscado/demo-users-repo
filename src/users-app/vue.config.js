// vue.config.js
module.exports = {
  // options...
  
	devServer: {
		proxy: {
		  '^/login': {
			target: 'http://localhost:8080/',
			ws: true,
			changeOrigin: true
		  },
		  '^/api': {
			target: 'http://localhost:8080/',
			ws: true,
			changeOrigin: true
		  }
		}
	}
}
