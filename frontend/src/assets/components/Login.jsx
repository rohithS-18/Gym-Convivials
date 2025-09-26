import React, { useState } from 'react'
import Registration from './Registration'
import axios from 'axios'
// import { useAuth } from '../providers/AuthProvider'
// import { useNavigate } from 'react-router'

function Login() {
  const[warning,setWarning]=useState("")
//   const {handleAuth} = useAuth();
//   const navigate=useNavigate();
  const handleUserReg = async (userdetails) => {
    try {
      const res = await axios.post("http://localhost:8080/mlogin", userdetails);
      setWarning("")
      let jwt=res.data.jwt;
      console.log("jwt", jwt);
      localStorage.setItem("token", jwt)
    //   handleAuth(jwt)
      setWarning("Successful login")
    //    navigate("/home")
    } catch (e) {
      if (e.response && e.response.status === 401) {
        console.log("Unauthorized: Invalid username or password");
        console.log("Message:", e.response.data.errmsg);
        setWarning("Invalid username or password")
      } else {
        console.log("Error while logging in:", e);
      }
    }
  };
  
  return (
    <Registration 
        func={"Log in "}
        mode={login}
        handleuserreg={handleUserReg}
        warningmsg={warning}
    />
)
}

export default Login
