import axios from "axios";

const BASE_URL = `${import.meta.env.VITE_REACT_APP_BASE_URL}/api/v1`;

export const jsonAxios = axios.create({
  baseURL: `${BASE_URL}`,
  withCredentials: true,
  headers: {
    "Content-Type": "application/json",
  },
});

export const formAxios = axios.create({
  baseURL: `${BASE_URL}`,
  headers: {
    "Content-Type": "multipart/form-data",
  },
});
