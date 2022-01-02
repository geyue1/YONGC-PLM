const webpack = require('webpack');
const path = require('path');
const MiniCssExtractPlugin = require("mini-css-extract-plugin");
const HtmlWebpackPlugin = require("html-webpack-plugin");
const ProvidePlugin = webpack.ProvidePlugin;
module.exports={
		mode:'development',//'development' or 'production'
		entry:path.join(__dirname,'./src/main.js'),
		output:{
			path: path.join(__dirname,'./dist'),
			filename: 'bundle.js'
		},
		module: {
		    rules: [
		        {
		            test: /\.vue$/,
		            loader: 'vue-loader',
		            options: {
		                loaders: {
		                    css: MiniCssExtractPlugin.loader
		                }
		                // other vue-loader options go here
		            }
		        },
		        {
		            test: /\.js$/,
		            loader: 'babel-loader',
		            exclude: /node_modules/
		        },
		        {
		            test: /\.(png|jpg|gif|svg)$/,
		            loader: 'url-loader',
		            options: {
		                name: "image/[name].[ext]?[hash]",
		                limit: 8192,

		            }
		        },
		        {
		            test: /\.(less|css)$/,
		            use: [
	                	MiniCssExtractPlugin.loader,
	                	"css-loader",
	                	"less-loader"
	                ]
		        },
		        {
		            test: /\.(eot|svg|ttf|woff|woff2)(\?\S*)?$/,
		            loader: 'file-loader'
		            
		        },
		        {
		            test: /\.(png|jpe?g|gif|svg)(\?\S*)?$/,
		            loader: 'file-loader',
		            query: {
		                name: '[name].[ext]?[hash]'
		            }
		        }

		    ]
		},
		resolve:{
			alias:{
				"vue$":'vue/vue2.6.1.js',
				'jquery$':'jquery/dist/jquery.min.js',
				'element-ui$':'vue/element-ui2.12.js'
			}
		},
		optimization: {
	        splitChunks: {
	            cacheGroups: {
	                commons: {
	                    name: "commons",
	                    chunks: "initial",
	                    minChunks: 2
	                }
	            }
	        }
	    },
		plugins: [
			new HtmlWebpackPlugin({
			    	template:'./src/index.html',
			    	filename:'index.html'
			    }),
		    new MiniCssExtractPlugin({
		        filename: 'styles.css',
		    })
		]
}