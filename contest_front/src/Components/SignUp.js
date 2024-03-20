import React, { useState } from 'react';
import AppService from "../Service/AppService";
import { useAuth } from "../Context/AuthContext";
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function SignUp() {
    const { setIsLoggedIn, setUserName } = useAuth();
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [telephone, setTelephone] = useState("");
    const [password, setPassword] = useState("")
    const [isMechanic, setIsMechanic] = useState(false);

    const handleSubmit = async (event) => {
        event.preventDefault();
        const formdata = {
            name: name,
            email: email,
            telephone: telephone,
            password:password,

        };
        try {
               await AppService.register(formdata, isMechanic);
                setUserName(sessionStorage.getItem("userName"));
                setIsLoggedIn(true);
                toast.success("You have successfully logged in")
        } catch (error) {
            toast.error("Error signing", error)
        }
    };

    return (
        <div className="max-w-md mx-auto bg-white rounded-lg overflow-hidden shadow-md">
            <div className="px-6 py-8">
                <h2 className="text-2xl font-semibold text-gray-800 mb-6">Sign Up</h2>
                <ToastContainer/>
                <form onSubmit={handleSubmit} autoComplete="off">
                    <div className="mb-4">
                        <label htmlFor="name" className="block text-gray-700 font-semibold mb-2">Name</label>
                        <input type="text" id="name" value={name} onChange={(e) => setName(e.target.value)}
                               className="w-full px-3 py-2 text-gray-700 border border-gray-300 rounded-lg focus:outline-none focus:border-blue-500"
                               required
                        />
                    </div>
                    <div className="mb-4">
                        <label htmlFor="email" className="block text-gray-700 font-semibold mb-2">Email</label>
                        <input type="email" id="email" value={email} onChange={(e) => setEmail(e.target.value)}
                               className="w-full px-3 py-2 text-gray-700 border border-gray-300 rounded-lg focus:outline-none focus:border-blue-500"
                               required
                        />
                    </div>
                    <div className="mb-4">
                        <label htmlFor="telephone" className="block text-gray-700 font-semibold mb-2">Telephone</label>
                        <input type="text" id="telephone" value={telephone} onChange={(e) => setTelephone(e.target.value)}
                               className="w-full px-3 py-2 text-gray-700 border border-gray-300 rounded-lg focus:outline-none focus:border-blue-500"
                        />
                    </div>
                    <div className="mb-4">
                        <label htmlFor="password" className="block text-gray-700 font-semibold mb-2">Password</label>
                        <input type="password" id="password" value={password} onChange={(e) => setPassword(e.target.value)}
                               className="w-full px-3 text-gray-800 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-blue-500"
                               autoComplete="off" />
                    </div>

                    <div className="mb-4 flex justify-between items-center">
                        <label className="text-gray-700 text-sm">Register as Mechanic</label>
                        <label className="switch">
                            <input type="checkbox" onChange={() => setIsMechanic(!isMechanic)} />
                            <span className="slider"></span>
                        </label>
                    </div>
                    <div className="flex justify-center">
                        <button
                            type="submit"
                            className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-md"
                        >
                            Sign Up
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default SignUp;
