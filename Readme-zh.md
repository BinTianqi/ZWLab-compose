# Zero Width Lab

[English](Readme.md)

## 这个项目已经停止维护

显然，这个项目不适合使用Kotlin + Compose，那太笨
重了。我用Vue + Vite + TypeScript重新制作了一个Z
WLab，你可以访问[这个网页](https://zwlab.pages.dev/)，或者查看[源代码](https://github.com/BinTianqi/ZWLab/)

## 简介

使用 Kotlin + Compose multiplatform 制作

支持的平台: ~~Windows,~~ Android, ~~web(wasmJs)~~

## 功能

- 复制零宽度字符到剪切板
- 向一段文字中加入零宽度字符
- 从一段文字中移除零宽度字符
- 把一段文字隐藏在另一段文字中
- 提取上一个功能生成的隐藏文字

## 零宽字符

常见的零宽字符：

- U+200B: Zero-width space / 零宽度空格
- U+FEFF: Zero-width no-break space / 零宽度非断空格
- U+200C: Zero-width non-joiner / 零宽度断字符
- U+200D: Zero-width joiner / 零宽度连字符
- U+200E: Left-to-right mark / 左至右符
- U+200F: Right-to-left mark / 右至左符

## License

The MIT License

> Copyright <2024> <Bin Tianqi>
>
> Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
>
> The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
>
> THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
