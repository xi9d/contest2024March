import React, { useState } from "react";
import AppService from "../Service/AppService";
import {useNavigate} from "react-router";

function Main() {
    const [mechanics, setMechanics] = useState([]);
     const navigate = useNavigate();


    const fetchNearMechanics = async () => {
        try {
            const response = await AppService.getAllMechanics();
            setMechanics(response.data);
        } catch (error) {
            console.error("Could not retrieve the mechanics", error);
        }
    }

    const getCurrentLocation = () => {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(
                (position) => {
                    const currentLocation = {
                        latitude: position.coords.latitude,
                        longitude: position.coords.longitude
                    };
                    console.log(currentLocation)

                },
                (error) => {
                    console.error("Error getting current location:", error);
                }
            );
        } else {
            console.error("Geolocation is not supported by this browser.");
        }
    }

    const handleSubmit = () => {
        getCurrentLocation();
        setTimeout(fetchNearMechanics, 3000); // Delay fetchNearMechanics by 3 seconds (3000 milliseconds)


    }
    const handleViewMechanic = (id) =>{
        navigate(`/view?id=${id}`,{state:{id:id}});
    }

    return (
            <div className="max-w-4xl mx-auto py-8">
                <div className="text-center mb-6">
                    <button onClick={handleSubmit} className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
                        Mechanics Near Me
                    </button>
                </div>
                <hr className="mb-6" />
                <h1 className="text-2xl font-bold mb-4">Available Mechanics</h1>
                <table className="w-full border-collapse border border-gray-300">
                    <thead className="bg-gray-200">
                    <tr>
                        <th className="border border-gray-300 px-4 py-2">ID</th>
                        <th className="border border-gray-300 px-4 py-2">Name</th>
                        <th className="border border-gray-300 px-4 py-2">Email</th>
                        <th className="border border-gray-300 px-4 py-2">Telephone</th>
                        <th className="border border-gray-300 px-4 py-2">Organization Name</th>
                        <th className="border border-gray-300 px-4 py-2">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {mechanics.map((mechanic, index) => (
                        <tr key={mechanic.id} className={index % 2 === 0 ? 'bg-gray-100' : 'bg-white'}>
                            <td className="border border-gray-300 px-4 py-2">{mechanic.id}</td>
                            <td className="border border-gray-300 px-4 py-2">{mechanic.fullName}</td>
                            <td className="border border-gray-300 px-4 py-2">{mechanic.email}</td>
                            <td className="border border-gray-300 px-4 py-2">{mechanic.telephone}</td>
                            <td className="border border-gray-300 px-4 py-2">{mechanic.organization}</td>
                            <td className="border border-gray-300 px-4 py-2">
                                <button onClick={() => handleViewMechanic(mechanic.id)} className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-1 px-2 rounded">
                                    View Mechanic
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        );
    }

    export default Main;


