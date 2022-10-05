import React from "react";

export default function Modal(props){
    const { open, close, header } = props;
    return (
        <div className={open ? 'openModal modal' : 'modal'}>
            {open ? (
                <section className="modal-sec">
                    <header>
                        {header}
                    </header>
                    <main>
                        <span className={'modal-span'}>
                                <button className="close" onClick={close}>
                                    &times;
                                </button>
                            </span>
                        {props.children}
                    </main>
                </section>
            ) : null}
        </div> 
    );
}