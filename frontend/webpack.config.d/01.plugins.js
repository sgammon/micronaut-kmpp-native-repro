(function (config) {
    config.module.rules.push({
        test: /\.hbs$/,
        loader: 'handlebars-loader'
    });
})(config);
