(function (config) {
    const HtmlWebpackPlugin = require('html-webpack-plugin');

    const rootTemplate = `
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>{{htmlWebpackPlugin.options.title}}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<main>
    <h1>Hello</h1>
    <div id="root"></div>
</main>
</body>
</html>
`;

    config.plugins.push(new HtmlWebpackPlugin({
        title: 'Demo App',
        filename: 'index.hbs',
        templateContent: rootTemplate,
        publicPath: '/static'
    }));
})(config);
