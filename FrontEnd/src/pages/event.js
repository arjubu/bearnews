import React, { useEffect } from 'react'
import FullCalendar, { formatDate } from '@fullcalendar/react'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from "@fullcalendar/interaction";
import timeGridPlugin from "@fullcalendar/timegrid";
import { useRef } from "react";
import { INITIAL_EVENTS, createEventId } from './eventFunction'
import moment from 'moment'
import eventService from '../services/event-service';
import Image from "next/image";
import Link from "next/link";


export default class Event extends React.Component {
 constructor() {
        super();
    };
  state = {
    weekendsVisible: true,
    currentEvents: [],
    count : 0,
  }




componentDidMount(){
this.getEvent();
}

async getEvent(){
 await eventService.getAllEvent().then((response) => {
      this.setState({
        currentEvents: response.data
      });
    });
}



  render() {
  console.log(this.state.currentEvents);
    return (
      <div className='demo-app'>
        {this.renderSidebar()}
        <div className='BackIconEvent'>
                <Link href="/">
                          <a>
                            <Image
                              src="/images/Bear_Mark_1_Color_01.jpg"
                              alt="brand-logo"
                              width={50}
                              height={40}
                            />
                          </a>
                        </Link>
                </div>
        <div className='demo-app-main'>
          <FullCalendar
            plugins={[dayGridPlugin, timeGridPlugin, interactionPlugin]}
            headerToolbar={{
              left: 'prev,next today',
              center: 'title',
              right: 'dayGridMonth,timeGridWeek,timeGridDay'
            }}
            initialView='dayGridMonth'
            editable={true}
            selectable={true}
            selectMirror={true}
            dayMaxEvents={true}
            weekends={this.state.weekendsVisible}
            initialEvents={this.state.testEvent}
            select={this.handleDateSelect}
            eventContent={renderEventContent}
            eventClick={this.handleEventClick}
            eventsSet={this.handleEvents}
          />
        </div>
      </div>


    )
  }

  renderSidebar() {
  console.log(INITIAL_EVENTS);
    return (
      <div className='demo-app-sidebar'>
        <div className='demo-app-sidebar-section'>
          <h2>All Events ({this.state.currentEvents.length})</h2>
          <ul>
            {this.state.currentEvents.map(renderSidebarEvent)}
          </ul>
        </div>
      </div>
    )
  }

  handleWeekendsToggle = () => {
    this.setState({
      weekendsVisible: !this.state.weekendsVisible
    })
  }

   handleDateSelect (selectInfo) {
    let eventDate = selectInfo.startStr
    let currentDate = moment().format("YYYY-MM-DD")
    if (moment(eventDate).isBefore(currentDate, 'day')) {
      alert('Sorry, you can not set event on past date')
    }
    else {
      let title = prompt('Please enter a new title for your event')
      let calendarApi = selectInfo.view.calendar
      calendarApi.unselect() // clear date selection
      if (title) {
        const event = {
          title: title,
          start: selectInfo.startStr,
        }
        eventService.saveEvent(event).then((res) => {
        window.location.reload(false);
         alert("Event save success");
        })
      }
    }
  }

  handleEventClick = (clickInfo) => {
    if (window.confirm(`Are you sure you want to delete the event '${clickInfo.event.title}'`)) {
      clickInfo.event.remove()
    }
  }

  handleEvents = (events) => {
    eventService.getAllEvent().then((response) => {
      this.setState({
        currentEvents: response.data
      });
    });
  }

}

function renderEventContent(currentEvents) {

  return (
    <>
      <b>{currentEvents.startdate}</b>
      <i>{currentEvents.title}</i>
    </>
  )
}

function renderSidebarEvent(event) {
  return (
    <li key={event.id}>
      <b>{formatDate(event.startdate, { year: 'numeric', month: 'short', day: 'numeric' })}</b>
      <i>{event.title}</i>
    </li>
  )
}
