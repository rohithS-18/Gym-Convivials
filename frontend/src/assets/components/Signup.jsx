import React, { useState } from 'react'
import Registration from './Registration'
import axios from 'axios'
// import { useNavigate } from 'react-router'

function Signup() {
//   const navigate=useNavigate();
    const[warning,setWarning]=useState();
  function handleSignup(userdetails){
    try{
      axios.post("http://localhost:8080/register",userdetails)
      .then((res)=>{
        console.log(res.data)
      })
      setWarning("Successful registration")
    //   navigate("/login");
    }

    catch(e){
      console.log("Error while signing up",e)
      setWarning(" registration unsuccessful")
    }
      
  }
  return (    
    <Registration 
        func={"Sign up"}
        mode={signup}
        handleuserreg={handleSignup}
        warningmsg={warning}
    />
  )
}

export default Signup
