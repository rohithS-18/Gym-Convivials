import {Outlet,Navigate} from "react-router-dom";
import { useAuth } from "../providers/AuthProvider"

const ProtectedRoutes=()=>{
    const{token, setToken} =useAuth();
    const user=token
    return user ? <Outlet/> : <Navigate to="/login"/>
}

export default ProtectedRoutes;
