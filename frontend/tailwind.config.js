export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      width: {
        // 22: "5.5rem",
        // 23: "5.8rem",
        // 29: "7.25rem",
        50: "12.5rem",
        70: "17.5rem",
        96: "24rem",
        100: "25rem",
        // 108: "27rem",
        // 112: "28rem",
        // 125: "31.25rem",
        140: "35rem",
        150: "37.5rem",
        160: "40rem",
      },
      height: {
        75: "18.75rem",
        140: "35rem",
      },
      colors: {
        primary: {
          DEFAULT: "#2bca43",
          maincolor: "#D7E1F5",
        },
      },
    },
  },
  plugins: [],
};
