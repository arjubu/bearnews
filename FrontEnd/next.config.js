/** @type {import('next').NextConfig} */
// const nextConfig = {
//   reactStrictMode: false,
//   swcMinify: true,
//   basePath: process.env.NODE_ENV === 'production' ? process.env.NEXT_PUBLIC_BASEPATH : "",
//   webpack: (config) => {
//     config.resolve.fallback = { fs: false };
//     return config;
//   },
// }


const withTM = require("next-transpile-modules")([
 
  "@babel/preset-react"
  
]);

module.exports = withTM({
  // your custom config goes here
  reactStrictMode: false,
  swcMinify: true,
  basePath: process.env.NODE_ENV === 'production' ? process.env.NEXT_PUBLIC_BASEPATH : "",
  webpack: (config) => {
    config.resolve.fallback = { fs: false };
    return config;
  },
});
//module.exports = nextConfig
