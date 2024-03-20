import React, { useState, useEffect } from 'react';
import AppService from '../Service/AppService';


function ViewMechanics({id}) {
    const [mechanic, setMechanic] = useState(null);

    const fetchMechanic = async () => {
        try {
            const response = await AppService.getMechanicById(id);
            setMechanic(response.data);
        } catch (error) {
            console.error('Error fetching mechanic:', error);
        }
    };
    useEffect(() => {
        fetchMechanic();
    },[] );
const handleCallMechanic = ()=>{

}
    return (
        <div className="container mx-auto px-4 mt-8">
            {mechanic ? (
                <div>
                    <h1 className="text-2xl font-semibold mb-4">{mechanic.name}</h1>
                    <p><strong>Email:</strong> {mechanic.email}</p>
                    <p><strong>Telephone:</strong> {mechanic.telephone}</p>
                    <button onClick={handleCallMechanic}>Call Mechanic</button>
                </div>
            ) : (
                <p>Loading mechanic details...</p>
            )}
        </div>
    );
}

export default ViewMechanics;
