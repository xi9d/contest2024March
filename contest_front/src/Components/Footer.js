import React from 'react';

function Footer() {
    return (
        <footer className="bg-gray-800 text-white py-4">
            <div className="container mx-auto px-4">
                <p className="text-center text-sm">© 2024 Your Company. All rights reserved.</p>
                <div className="flex justify-center mt-2">
                    <a href="#" className="mr-4 text-sm hover:text-blue-400">Privacy Policy</a>
                    <a href="#" className="mr-4 text-sm hover:text-blue-400">Terms of Service</a>
                    <a href="#" className="text-sm hover:text-blue-400">Contact Us</a>
                </div>
            </div>
        </footer>
    );
}

export default Footer;
