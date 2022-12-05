import React, { useEffect, useRef ,useState} from 'react';

const FormGroup = ({pClass, label, type, name, rows }) => {
    const [comment, setComment] = useState("");

    const inputElement = useRef();

    const InputFocusUI = () => {
        const selectElm = inputElement.current;
        const parentElm = inputElement.current.parentElement;

        selectElm.addEventListener('focusin', (e) => {
            parentElm.classList.add("focused");
        })
        selectElm.addEventListener('focusout', (e) => {
            if (!selectElm.value) {
                parentElm.classList.remove("focused");
            }
        })
    }

    useEffect(() => {
        InputFocusUI();
    }, []);

    return ( 
        <div className={`form-group ${pClass}`}>
            {label ? <label>{label}</label> : ""}
            {type === "textarea" ? 
            <textarea type={type} name={name} ref={inputElement} rows={rows ?? 3} required onChange={(e)=>{console.log(e.target.value)}} />: 
            <input type={type} name={name} ref={inputElement} required/> 
            }
        </div>
    );
}
 
export default FormGroup;