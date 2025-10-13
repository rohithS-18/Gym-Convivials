import React, { useState } from 'react'
import Registration from './Registration'
import axios from 'axios'
import { Navigate, useNavigate } from 'react-router-dom';
import { useAuth } from '../providers/AuthProvider';

function Login() {
  const navigate=useNavigate();
  const[warning,setWarning]=useState("");
  const{token,setToken}=useAuth();
  const handleUserReg = async (userdetails) => {
    try {
      const res = await axios.post("http://localhost:8080/mlogin", userdetails);
      // console.log("mlogon",res.data);
      setWarning("");    
      setWarning("Successful login");
      setToken(res.data);
      navigate("/test");
      const res2=await axios.get("http://localhost:8080/get-cookie", {
        headers:{
          Authorization: `Bearer ${res.data}`
        }
      });
    } catch (e) {
      console.log(e);
      if (e.response) {
        console.log("Unauthorized: Invalid username or password");
        console.log("Message:", e.response.data.errmsg);
        setWarning("Invalid username or password");
      } else {
        console.log("Error while logging in:", e);
      }
    }
  };
  
  return (
    <Registration 
        mode={"Login"}
        handleuserreg={handleUserReg}
        warningmsg={warning}
    />
)
}

export default Login;
