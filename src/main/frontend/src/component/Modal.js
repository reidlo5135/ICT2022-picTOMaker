import React, {Component, useState} from "react";
import ModalCss from '../css/Modal.css';

export class Modal extends Component {
    render() {
        const { open, close, header } = this.props;

        return (
            <div className={open ? 'openModal modal' : 'modal'}>
                {open ? (
                    <section>
                        <header>
                            {header}
                            <button className="close" onClick={close}>
                                &times;
                            </button>
                        </header>
                        <main>{this.props.children}</main>
                        <footer>
                            <button className="close" onClick={close}>
                                close
                            </button>
                        </footer>
                    </section>
                ) : null}
            </div>
        );
    }
}