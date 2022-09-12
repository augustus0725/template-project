import React from "react";

// 演示使用 context 的例子
export const themes = {
    light: {
      foreground: "#000000",
      background: "#eeeeee"
    },
    dark: {
      foreground: "#ffffff",
      background: "#222222"
    }
  };
  
export const ThemeContext = React.createContext(themes.light);