const path = require('path')
const svgFilePaths = ["svg"].map((v) => path.resolve(v))
const tagName = 'icon'

module.exports = {
    tagName,
    svgFilePath: svgFilePaths,
    svgoConfig: {},
    pathAlias: {},
    transformAssetUrls: {},
}
