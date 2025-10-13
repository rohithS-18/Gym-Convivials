import React, { createContext, useContext, useState, useMemo } from "react";

export const AuthContext = createContext();

export const useAuth = () => {
  const authContext = useContext(AuthContext);
  if (!authContext) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return authContext;
};

// Safe JWT decode function
function getJWTClaims(token) {
  if (!token) return null;
  try {
    const payload = token.split(".")[1];
    // Replace URL-safe base64 chars
    const decoded = atob(payload.replace(/-/g, "+").replace(/_/g, "/"));
    return JSON.parse(decoded);
  } catch (e) {
    console.error("Invalid JWT token", e);
    return null;
  }
}

export const AuthProvider = ({ children }) => {
  const [token, setTokenState] = useState(() => localStorage.getItem("token"));

  // Decode user details safely
  const userDetails = useMemo(() => {
    const claims = getJWTClaims(token);
    if (!claims) return null;
    return {
      userId: claims.userId,
      username: claims.username,
    };
  }, [token]);

  // Update localStorage + state when token changes
  const setToken = (newToken) => {
    if (newToken) {
      localStorage.setItem("token", newToken);
    } else {
      localStorage.removeItem("token");
    }
    setTokenState(newToken);
  };

  return (
    <AuthContext.Provider value={{ token, setToken, userDetails }}>
      {children}
    </AuthContext.Provider>
  );
};
