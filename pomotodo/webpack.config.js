var path = require('path');
var webpack = require('webpack');
var json = require('./package.json');

var htmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
    entry:      {
        app:    './src/index.js',
        vendor: Object.keys(json.dependencies)
    },
    output: {
        path:       path.resolve(__dirname, './dist'),
        filename:   '[name].[chunkhash:8].js',
    },
    resolve: {
        modules: [
            'node_modules',
            path.resolve(__dirname, './src')
        ],

        alias: {
            'vue$': 'vue/dist/vue.common.js'
        }
    },
    module: {
        rules: [
            {test: /\.js$/, use: 'babel-loader'}
        ]
    },
    devServer: {
        contentBase: path.join(__dirname, "dist")
    },
    plugins: [
        new htmlWebpackPlugin({
            filename: './index.html',
            template: './src/index.ejs',
            title:    'pomotodo'
        }),
        new webpack.optimize.CommonsChunkPlugin({
            name: 'vendor',
            filename: '[name].[chunkhash:8].js'
        })
    ]
};