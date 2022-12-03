import React, { useState , Component} from "react";
//import ReactDOM from "react-dom";
import HeadMeta from "../components/elements/HeadMeta";
import logo from '../../public/images/Bear_Mark_1_Color_01.jpg';
import backgroundImage from '../../public/images/Background.jpg';
import HeaderOne from "../components/header/HeaderOne";
import Image from "next/image";
import Select from 'react-select';
import Creatable from 'react-select/creatable';
import { useCookies } from 'react-cookie';

import {
MDBBtn,
MDBContainer,
MDBRow,
MDBCol,
MDBCard,
MDBCardBody,
MDBInput,
MDBTextArea,
MDBFile } from 'mdb-react-ui-kit';

function CreateArticle() {
const [Title, setTitle] = useState();
const [Tags, setTags] = useState();
const [Context, setContext] = useState();
const [Files, setFiles] = useState();
const [cookies, setCookie] = useCookies(['username'])


function SearchResultList (){
const [DATASET, setDataset] = useState();
  
    function handlerChange(input){
    //setsearchValue(input);
        fetch('http://localhost:8080/getTagByLetter', {
    method: 'POST',
    body: JSON.stringify({
        suggString : input,
    }),
    headers: {
        "Content-type": "application/json; charset=UTF-8"
    }
    }
    )
    .then(response => {
           
        if (response.status == 200) {
        //console.log('this works'); 
        return response.json();
            
        } else {
            
      //  throw new Error('Something went wrong ...');
    
        }
            
        }).then(data=>{
        let a = [];
        console.log(data);
        if(data.data == "doesn't exsist"){
            setDataset(a);
            return data;
        }
        data.data.forEach(myfunction)
        function myfunction(item){
        let b = {label:item.toString()};
        a.push(b);
        }
        setDataset(a);
          
        return data;
        //console.log(Mylist);
        });
        //console.log(DATASET);
  
}
  
return(
    //const resultItems = this.props.data;
   
    <div className="upload-tag">
        {console.log("DATASET")}
        {console.log(DATASET)}
        <Creatable
        onInputChange = {(event) => handlerChange(event) }
        options={DATASET}
        placeholder="Choose only one tag please"
        getOptionValue={(option) => option.label}
        //inputValue={this.state.searchKey}
        onChange={(opt) => handleTag(opt)}

          

        />
    </div>
      
);
}

function handleTag(input){
    
if(input.__isNew__){
    //fetch --------------------------------------------------
    // fetch('http://localhost:8080/createTag', {
    //   method: 'POST',
    //   body: JSON.stringify({
    //     tagText: input.lable,

    //   }),
    //   headers: {
    //     "Content-type": "application/json; charset=UTF-8"
    //   }
    // })
    //   .then(response => {
           
    //     if (response.status == 200) {
    //       console.log('go'); 
    //       return response.json();
            
    //     } else {
    //       error_login({ name: "ID", message: errors.username});
    //       throw new Error('Something went wrong ...');
  
    //     }
            
    //     }).then(data=>{
    //     });
    //end fetch -------------------------------------------
    console.log(input.label);
}
setTags(input.label);
console.log(input.label);

    }
    const handleDangerConsole = (event) => {
        event.preventDefault();
        console.log("---danger button calling--");
        console.log(Title);
        console.log(Context);
        console.log(Tags);
        console.log(cookies.username)
       // console.log()

        fetch('http://localhost:8080/createArticle', {
            mode: 'cors',
            method: 'POST',
            body: JSON.stringify({
                articleTitle: Title,
                articleContent: Context,
                tagContainingText: Tags,
                createdUsersEmail: cookies.username,
            }),
            headers: {
                'Content-type': 'application/json',
            },
        })
            .then((response) => {
                console.log('response', response);
                if (response.status == 201) {
                    console.log('goes to backend');
                    return response.json();
                } else {
                    console.log(response.responseMessage.message)
                   // throw new Error('Something went wrong ...');
                }
            })
    }
return ( 
    <>
      
    <HeadMeta metaTitle="Home One"/>
    <HeaderOne />

    {/* <div className="appcreate">
    <div className="create_frame">
    </div>
    </div> */}
    <MDBContainer fluid>

<MDBRow className='d-flex justify-content-center align-items-center'>
<MDBCol lg='9' className='my-5'>

<h1 className="text-white mb-4">Create Article</h1>
<form onSubmit={handleDangerConsole}>
<MDBCard>
    <MDBCardBody className='px-4'>

    <MDBRow className='align-items-center pt-4 pb-3'>

        <MDBCol md='3' className='ps-5'>
        <h6 className="mb-0">Title</h6>
        </MDBCol>

        <MDBCol md='9' className='pe-5'>
        <MDBInput label='Title' size='lg' id='form1' type='text' onChange={e => setTitle(e.target.value)}/>
        </MDBCol>

    </MDBRow>

    <hr className="mx-n3" />

    <MDBRow className='align-items-center pt-4 pb-3'>

        <MDBCol md='3' className='ps-5'>
        <h6 className="mb-0">Tags</h6>
        </MDBCol>
        <MDBCol md='9' className='pe-5'>
        <SearchResultList></SearchResultList>
        </MDBCol>

    </MDBRow>

    <hr className="mx-n3" />

    <MDBRow className='align-items-center pt-4 pb-3'>

        <MDBCol md='3' className='ps-5'>
        <h6 className="mb-0">Context</h6>
        </MDBCol>

        <MDBCol md='9' className='pe-5'>
        <MDBTextArea label='Context' id='textAreaExample' rows={3} onChange={e => setContext(e.target.value)}/>
        </MDBCol>

    </MDBRow>

    <hr className="mx-n3" />

    <MDBRow className='align-items-center pt-4 pb-3'>

        <MDBCol md='3' className='ps-5'>
        <h6 className="mb-0">Upload Picture or Media</h6>
        </MDBCol>

        <MDBCol md='9' className='pe-5'>
        <MDBFile size='lg' id='customFile' onChange={e => setFiles(e.target.files)} />
        <div className="small text-muted mt-2">Upload your picture if you want. Max file size 50 MB</div>
        </MDBCol>

    </MDBRow>

    <hr className="mx-n3" />

<button type="submit" className="btn btn-danger">Danger</button>

</MDBCardBody>
                             
</MDBCard>
</form>
</MDBCol>
</MDBRow>

</MDBContainer>

    </>
    );
}
   
export default CreateArticle;