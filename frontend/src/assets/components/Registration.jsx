import React, { useEffect, useRef } from 'react';

function Registration({ func, mode, handleuserreg, warningmsg }) {
  const warning = useRef();   
   useEffect(()=>{
            warning.current.textContent=warningmsg
        },[warningmsg])

    const handleUserSubmit=(e)=>{
    e.preventDefault();
    if(mode==signup && e.target.password.value!==e.target.repassword.value){
              warning.current.textContent="Passwords do not match"
        }              
        else{
          const userDetails={
            'username':e.target.username.value,
            'pasword':e.target.password.value,
          }
          handleuserreg(userDetails);       

      }
  }
  return (
    <div className="flex h-screen w-screen bg-gray-900 text-white">
      {/* Login form - left side */}
      <div className="flex flex-col justify-center items-center w-1/2">
        <div className="relative w-[340px] bg-black bg-opacity-30 px-8 py-10 rounded-xl shadow-2xl neon-border">
          {/* Neon corners (custom with pseudo-elements) */}
          <h2 className="text-5xl font-serif font-bold mb-10 text-white text-center">Login</h2>
          <form className="space-y-6" onSubmit={handleUserSubmit}>
            <div>
              <label className="block mb-1 font-medium text-white"></label>
              <input
                type="text"
                name="username"
                placeholder="Enter your username"
                className="w-full px-4 py-2 rounded-md bg-transparent border border-cyan-400 text-white placeholder-cyan-300 focus:outline-none focus:ring-2 focus:ring-cyan-400"
              />
            </div>
            <div>
              <label className="block mb-1 font-medium text-white"></label>
              <input
                name="password"
                type="password"
                placeholder="Enter your password"
                className="w-full px-4 py-2 rounded-md bg-transparent border border-cyan-400 text-white placeholder-cyan-300 focus:outline-none focus:ring-2 focus:ring-cyan-400"
              />
            </div>
            {mode==signup && (
              <>
              <label className="block mb-1 font-medium text-white"></label>
                <input
                  type="text"
                  name="repassword"
                  placeholder="Confirm password"
                className="w-full px-4 py-2 rounded-md bg-transparent border border-cyan-400 text-white placeholder-cyan-300 focus:outline-none focus:ring-2 focus:ring-cyan-400"             
                />                
              </>
            )}
            <p style={{margin:'0',color:'#dc3545',textAlign:'center'}} ref={warning}></p>
            <button
              type="submit"
              className="w-full py-3 rounded-md bg-cyan-400 text-gray-900 font-semibold mt-4 neon-button focus:outline-none"
            >              Submit
            </button>
            <p >
              {mode==login && (
                <a
                  href="/signup"
                  style={{ color: '#004182', textDecoration: 'none' }}
                >
                  Don't have an account? Register
                </a>
              )}
              {
                mode==signup && (
                  <a
                  href="/login"
                  style={{ color: '#004182', textDecoration: 'none' }}
                >
                  Already have an account? Login
                </a>
                )
              }
            </p>
          </form>
        </div>
      </div>
      {/* Image - right side */}
      <div
        className="w-1/2 bg-cover bg-center"
        style={{
          backgroundImage:
            "url('/loginbgpng.png')",
        }}
      ></div>
      {/* Additional neon border and button effect */}
      <style jsx>{`
        .neon-border {
          box-shadow:
            0 0 8px #06faff,
            0 0 24px #06faff,
            0 0 40px #06faff;
          border-radius: 15px;
        }
        .neon-border:before,
        .neon-border:after {
          content: '';
          position: absolute;
          width: 30px;
          height: 30px;
          border: 3px solid #06faff;
          border-radius: 8px;
        }
        .neon-border:before {
          top: -8px;
          left: -8px;
          border-right: 0;
          border-bottom: 0;
        }
        .neon-border:after {
          bottom: -8px;
          right: -8px;
          border-left: 0;
          border-top: 0;
        }
        .neon-button {
          box-shadow:
            0 0 12px #06faff,
            0 0 22px #06faff;
        }
      `}</style>
    </div>
  );
};

export default Registration;

