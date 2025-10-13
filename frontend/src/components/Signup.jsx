import React, { useState } from 'react'
import Registration from './Registration'
import axios from 'axios'
import { useNavigate } from 'react-router'

function Signup() {
  const navigate=useNavigate();
    const[warning,setWarning]=useState();
  async function handleSignup(userdetails){
    try{
      const res= await axios.post("http://localhost:8080/register",userdetails)
      setWarning("Successful registration")
       navigate("/login");
    }

    catch(e){
      console.log("Error while signing up",e)
      setWarning(" registration unsuccessful")
    }
      
  }
  return (    
    <Registration 
        mode={"Signup"}
        handleuserreg={handleSignup}
        warningmsg={warning}
    />
  )
}

export default Signup
