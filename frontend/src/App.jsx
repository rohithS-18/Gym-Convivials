import { useState } from 'react'
import './App.css'
import Login from './components/Login'
import Signup from './components/Signup'
import { createBrowserRouter,RouterProvider } from 'react-router-dom'
import ProtectedRoutes from './utils/ProtectedRoutes'
import Test from './components/test'
import GymOnboarding from './components/OnboardingCard'

function App() {
  const [count, setCount] = useState(0);
    const router = createBrowserRouter([
    {
      path:'/',
      element:<ProtectedRoutes/>,
      children:[
        {
        path: '/test',
        element: <Test/>        
       }
      ]
    },
    {
      path:"/login",
      element:<Login/>
    },
    {
      path:"/signup",
      element:<Signup/>
    }

  ]);
    return (
          <RouterProvider router={router} />

  );
  // return(
  //   <Test />
  // )
}

export default App
