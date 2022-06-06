import React from "react";

export default function Modal(props){
    const { open, close, header } = props;
    console.log("뿡")
    return (
        <div className={open ? 'openModal modal' : 'modal'}>
            {open ? (
                <section>
                    <header>
                        {header}
                    </header>
                    <main>
                        <span className={'modal-span'}>
                            </span>
                        {props.children}
                    </main>
                </section>
            ) : null}
        </div> 
    );
}