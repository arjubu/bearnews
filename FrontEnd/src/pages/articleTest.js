import { useEffect } from "react";
import "./App.css";
import { Routes, Route, useNavigate } from 'react-router-dom';

function ArticleTest() {
    const navigate = useNavigate();

    const navigateToContacts = () => {
        // 👇️ navigate to /contacts
        navigate('/contacts');
    };
    useEffect(() => {
        fetch('http://localhost:8080/fetchSystemArticles')
            .then((response) => response.json())
            .then((actualData) => console.log(actualData))
            .catch((err) => {
                console.log(err.message);
            });
    }, []);

    return (
        <div>
            <p>Hello, world!</p>
        </div>
    );
}

export default ArticleTest;