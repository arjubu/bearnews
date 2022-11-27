import React, { useState , Component} from "react";
//import ReactDOM from "react-dom";
import HeadMeta from "../components/elements/HeadMeta";
import logo from '../../public/images/Bear_Mark_1_Color_01.jpg';
import backgroundImage from '../../public/images/Background.jpg';
import HeaderOne from "../components/header/HeaderOne";
import Image from "next/image";
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

    <h1 class="text-white mb-4">Create Article</h1>

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
            <MDBInput label='tags' size='lg' id='form2' type='text' onChange={e => setTags(e.target.value)}/>
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
            <h6 className="mb-0">Upload Picture</h6>
          </MDBCol>

          <MDBCol md='9' className='pe-5'>
            <MDBFile size='lg' id='customFile' />
            <div className="small text-muted mt-2">Upload your picture if you want. Max file size 50 MB</div>
          </MDBCol>

        </MDBRow>

        <hr className="mx-n3" />

        <button type="button" class="btn btn-danger">Danger</button>

      </MDBCardBody>
    </MDBCard>

  </MDBCol>
</MDBRow>

</MDBContainer>

      </>
     );
  }
   
  export default CreateArticle;